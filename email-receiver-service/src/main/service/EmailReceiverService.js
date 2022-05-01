import imapClient from 'imap-simple'
import MessageService from "./MessageService.js";

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
                    // markSeen: true
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
    }
}

export default EmailReceiverService
