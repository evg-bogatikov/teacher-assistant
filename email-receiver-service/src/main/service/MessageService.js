import _ from "lodash";
import {simpleParser} from "mailparser";
import fs from "fs-extra";
import uuid from "uuid-random";
import path from "path";
import {fileURLToPath} from 'url'

const __filename = fileURLToPath(import.meta.url)
const __dirname = path.dirname(__filename)
let RESOURCES = path.resolve(__dirname, '../../resources')

const MessageService = {

    parseAllMessage(messages) {
        console.log(RESOURCES)
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
                        if (message.body === undefined)
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
        if (message.attachments.length === 0)
            return message

        let fileNameArray = []
        message.attachments.forEach(item => {
            let fileName = {
                "fileId": uuid(),
                "filename": item.filename
            }
            fs.outputFile(RESOURCES + '/static/' + fileName.fileId + '.' + fileName.filename, item.content)
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
