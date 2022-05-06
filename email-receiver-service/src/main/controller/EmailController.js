import {Router} from "express";
import EmailReceiver from "../service/EmailReceiverService.js";
import MessageService from "../service/MessageService.js";

const router = Router()

router.post("/api/v1/email/", (req, res) => {
    res.header("Content-Type", "application/json")
    if (req.body.email != null && req.body.password != null && req.body.host != null) {
        EmailReceiver.getAllEmails(req.body.email, req.body.password, req.body.host)
            .then(data => {
                let messageList = {
                    messageList: data
                }
                res.json(messageList)
            })
    }
    // res.sendStatus(400)
})

router.get("/api/v1/email/file/:fileId", (req, res,next) => {
    MessageService.getFileById(req.params.fileId, res,next)
})

export default router
