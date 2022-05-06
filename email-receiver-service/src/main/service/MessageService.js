import _ from "lodash";
import {simpleParser} from "mailparser";
import fsExtra from "fs-extra";
import fs from 'fs'
import uuid from "uuid-random";
import path from "path";
import {fileURLToPath} from 'url'

const __filename = fileURLToPath(import.meta.url)
const __dirname = path.dirname(__filename)
let RESOURCES = path.resolve(__dirname, '../../resources')

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
        let readJson = fsExtra.readJson(RESOURCES + "/targetWord.json")
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
            let file = {
                "fileId": uuid(),
                "filename": item.filename
            }
            fsExtra.outputFile(RESOURCES + '/static/' + file.fileId + '.' + file.filename, item.content)
                .then(err => {
                    if (err) {
                        return console.log(err);
                    }
                })
            fileNameArray.push(file)
        })
        message.attachments = fileNameArray
        return message
    },

    getFileById(fileId, res, next) {
        fs.readdir(RESOURCES + '/static/', (err, files) => {
            let file = files.filter(file => {
                let fId = file.split('.')[0];
                if(fileId === fId)
                    return file
            })[0]
            if(_.isEmpty(file)){
                next(new Error())
            }
            res.sendFile(RESOURCES + "/static/" + file)
        })
    }
}

export default MessageService
