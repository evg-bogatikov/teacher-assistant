import {Router} from "express";
import EmailReceiver from "../service/EmailReceiverService.js";

const router = Router()

router.post("/api/v1/email/",  (req, res) => {
    console.log("Request accept")
    console.log(req.body.email)
    res.header("Content-Type", "application/json")
    if (req.body.email != null && req.body.password != null && req.body.host != null) {
        EmailReceiver.getAllEmails(req.body.email, req.body.password, req.body.host)
            .then(data => {
                let messageList = {
                    messageList: data
                }
                console.log(messageList)
                res.json(messageList)
            })
    }
    // res.sendStatus(400)
})

export default router
