import {Router} from "express";
import EmailReceiver from "../service/EmailReceiverService.js";
import MessageService from "../service/MessageService.js";

const router = Router()

router.post("/api/v1/email/", (req, res, next) => {
    res.header("Content-Type", "application/json")
    EmailReceiver.getAllEmails(req.body.email, req.body.password, req.body.host, next)
        .then(data => {
            let messageList = {
                messageList: data
            }
            res.json(messageList)
        }).catch(next)
})

router.get("/api/v1/email/file/:fileId", (req, res, next) => {
    MessageService.getFileById(req.params.fileId, res, next)
})

export default router
