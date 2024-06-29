import { mysqlConneOpt } from "../ENV";
import { DbErr, MysqlPromise } from "../MySqlPromise";
import { DbSrc } from "../service/DbSrc";

async function main(){
	try {
		const db = await MysqlPromise.connect(mysqlConneOpt)
		const dbSrc = DbSrc.new(db)
		await dbSrc.initSchema()
	} catch (err) {
		if(err instanceof DbErr){
			console.error(err)
			console.error(err.sql)
		}
		throw err
	}finally{
		process.exit()
	}
}

main().catch(e=>{console.error(-1)})