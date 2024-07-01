import { DbErr, MysqlPromise } from "./MySqlPromise";
export const mysqlConneOpt = {
	host: 'localhost',
	user: 'root', 
	password: 'admin',
	database: 'dbcourse'
}

export const dbPromise = MysqlPromise.connect(mysqlConneOpt)

export const weatherKey = '86a49670b7fa8f883482b83776f599c0'

//https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=<用户key>


//https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=86a49670b7fa8f883482b83776f599c0