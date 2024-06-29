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
export type KeyAsValue<T> = {
	[K in keyof T]: K;
}