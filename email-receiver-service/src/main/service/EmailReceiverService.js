import imapClient from 'imap-simple'
import MessageService from "./MessageService.js";
import nodemailer from "nodemailer";

const INBOX = 'INBOX'

const EmailReceiverService = {
    getAllEmails(user, password, host) {
        const config = {
            imap: {
                user: user,
                password: password,
                host: host,
                port: 993,
                tls: true,
                authTimeout: 10000
            }
        }

        return imapClient.connect(config).then(connection => {
            return connection.openBox(INBOX).then(() => {
                let searchCriteria = ['UNSEEN']
                let fetchOptions = {
                    bodies: ['HEADER', 'TEXT', ''],
                    markSeen: true
                }

                return connection.search(searchCriteria, fetchOptions).then(messages => {
                    return MessageService.parseAllMessage(messages)
                        .then(parsedMessages => {
                            return MessageService.filterMessages(parsedMessages)
                                .then(filteredMessages =>{
                                    const result = []
                                    filteredMessages.forEach(message =>{
                                        result.push(MessageService.downloadFilesFromMessage(message))
                                    })
                                    return result
                                })
                        })
                })
            })
        })
    },

    sendEmail(sendMessageForm) {
        let transport = nodemailer.createTransport({
            host: sendMessageForm.host,
            port: 465,
            auth: {
                user: sendMessageForm.user,
                pass: sendMessageForm.pass,
            }
        });

        let message ={
            from: sendMessageForm.user,
            to: sendMessageForm.to,
            subject: sendMessageForm.subject,
            text: sendMessageForm.content
        }

        return transport.sendMail(message)
    }
}

export default EmailReceiverService
