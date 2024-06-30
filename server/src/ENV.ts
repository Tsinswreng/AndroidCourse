import { DbErr, MysqlPromise } from "./MySqlPromise";
export const mysqlConneOpt = {
	host: 'localhost',
	user: 'root', 
	password: 'admin',
	database: 'dbcourse'
}

export const dbPromise = MysqlPromise.connect(mysqlConneOpt)