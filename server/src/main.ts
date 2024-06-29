import { Server } from "./server";

async function main(){
	const server = await Server.New()
	await server.start()
}


main().catch(e=>{
	console.error(e)
})
