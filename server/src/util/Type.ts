/**
 * InstanceType_<typeof MyClass>
 */
export type InstanceType_<T extends { prototype: any }> = T["prototype"];
//export type InstanceType_<T> = T["prototype"];


/** 從字串字面量解析類型 */
export type ParseType<T extends string> =
	T extends 'string' ? string :
	T extends 'number' ? number :
	T extends 'boolean' ? boolean :
	T extends 'bigint' ? bigint :
	T extends 'symbol' ? symbol :
	T extends 'undefined' ? undefined :
	T extends 'object' ? object :
	T extends 'function' ? Function :
	never;

/** 基本數據類型 */
export type PrimitiveTypeStr = 'number' | 'string' | 'boolean' | 'null' | 'undefined' | 'bigint' | 'symbol';



/** 
 *
 * class A{
	a
	b
	c
	d
}
type Result = KeyAsValue<A>
Result === {a:'a', b:'b', c:'c', d:'d'}
 */
export type KeyMirror<T> = {
	[K in keyof T]: K;
}


/* 
除函數外、取對象ʹ䀬ʹ鍵ʹ聯合類型
for(const K of T){
	if(T[K] extends Function){
		return never
	}else{
		return K
	}
}
最後 [keyof T] 用于提取映射类型中的值并形成联合类型 卒得 "key1"|"key2"...
只取公開者
含 訪問器與修改器
*/
export type PubNonFuncKeys<T> = {
	/* for */[K in keyof T]: //{
		/* if */T[K] extends Function ? 
		/* then return */never 
		/* else return */: K
	//}
}[keyof T];

/** 
 * 除函數外、對象ʹ䀬ʹ鍵ˋʃ成對象ʹ類型ˇ取
 * 只取公開者
 * 含 訪問器與修改器
 */
export type PubNonFuncProp<T> = Pick<T, PubNonFuncKeys<T>>;
