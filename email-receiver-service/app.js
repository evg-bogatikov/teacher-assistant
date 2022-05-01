import express from 'express'
import emailController from "./src/main/controller/EmailController.js";

const PORT = 8082
const app = express()

app.use(express.json())
app.use(emailController)

app.listen(PORT)
