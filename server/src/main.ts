import { Server } from "server";

const server = Server.new()

async function main(){
	await server.start()
}


main().catch(e=>{
	console.error(e)
})
