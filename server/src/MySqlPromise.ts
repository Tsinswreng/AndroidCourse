import mysql from 'mysql';

export class DbErr extends Error{
	protected constructor(){super()}
	protected __init__(...args: Parameters<typeof DbErr.new>){
		const z = this
		z._err = args[0]
		z.sql = args[1]
		return z
	}

	static new(err:any, sql:str){
		const z = new this()
		z.__init__(err, sql)
		return z
	}

	//get This(){return DbErr}
	_err:any
	sql:str
	param?:any[]
}

/**
 * 用Promise封装的数据库操作、方便async/await
 */
export class MysqlPromise{
	/**
	 * 连接数据库
	 * @param opt 
	 * @returns Promise<数据库连接对象>
	 */
	static connect(opt:mysql.ConnectionConfig){
		const ans = mysql.createConnection(opt)
		return new Promise<mysql.Connection>((res,rej)=>{
			ans.connect((err)=>{
				if(err != void 0){
					rej(err);return
				}
				res(ans)
			})
		})
	}

	/**
	 * 执行sql
	 * @param db 数据库连接对象
	 * @param sql sql字符串
	 * @param values 值数组
	 * @returns Promise<[查詢結果, mysql.FieldInfo[]|undefined]>
	 */
	static query<T>(db:mysql.Connection, sql:string, values?:any[]){
		return new Promise<[T, mysql.FieldInfo[]|undefined]>((res,rej)=>{
			db.query(sql, values, (err, results, field)=>{
				if(err != void 0){
					const de = DbErr.new(err, sql)
					de.param = values
					rej(de);return
				}
				res([results, field])
			})
		})
		
	}
}


class Constrains{
	readonly PRIMARY_KEY = 'PRIMARY KEY'
	//readonly AUTO_INCREMENT = 'AUTO_INCREMENT'
	readonly AUTO_PRIMARY_KEY = 'AUTO_INCREMENT PRIMARY KEY'
	readonly NOT_NULL = 'NOT NULL'
}

export const CONSTRAINS = new Constrains()