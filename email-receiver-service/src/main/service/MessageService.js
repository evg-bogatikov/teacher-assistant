import _ from "lodash";
import {simpleParser} from "mailparser";
import fs from "fs-extra";
import uuid from "uuid-random";

const RESOURCES = '/home/evgeny/IdeaProjects/teacher-assistant/email-receiver-service/src/resources'

const MessageService = {

    parseAllMessage(messages) {
        const result = []
        messages.forEach(message => {
            let all = _.find(message.parts, {"which": ""})
            let id = message.attributes.uid;
            let idHeader = "Imap-Id: " + id + "\r\n";

            result.push(simpleParser(idHeader + all.body)
                .then(message => {
                    return {
                        messageId: message.messageId,
                        subject: message.subject,
                        from: message.from.text,
                        to: message.to.text,
                        date: message.date,
                        body: message.text,
                        attachments: message.attachments
                    }
                }))
        })
        return Promise.all(result)
    },

    filterMessages(messages) {
        let readJson = fs.readJson(RESOURCES + "/targetWord.json")
        return readJson.then(jsonData => {
            const filteredMessages = []
            messages.forEach(message => {
                jsonData.words.every(targetWord => {
                    if (message.subject.toLowerCase().includes(targetWord)) {
                        if(message.body === undefined)
                            message.body = ""
                        filteredMessages.push(message)
                        return false
                    }
                    return true
                })
            })
            return filteredMessages
        })
    },

    downloadFilesFromMessage(message) {
        if(message.attachments.length === 0)
            return message

        let fileNameArray = []
        message.attachments.forEach(item => {
            let fileName = uuid() + '.' + item.filename
            fs.outputFile(RESOURCES + '/static/' + fileName, item.content)
                .then(err => {
                    if (err) {
                        return console.log(err);
                    }
                })
            fileNameArray.push(fileName)
        })
        message.attachments = fileNameArray
        return message

    }
}

export default MessageService
