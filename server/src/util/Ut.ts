import * as Ty from './Type'

/**
 * 實例ᵗ類型轉化 並檢查
 * class Child extends Father{}
 * let idk:any = new Child()
 * let c = instanceAs(idk, Father) //c:Father ok
 * @param src 源實例
 * @param TargetClass 目標類
 * @param errMsg 
 * @returns 
 */
export function instanceAs<Target extends { prototype: any }>(src, TargetClass: Target, errMsg:any=''){
	if(TargetClass?.constructor == void 0){
		if( typeof errMsg === 'string' ){
			throw new Error(errMsg)
		}else{
			throw errMsg
		}
	}
	//@ts-ignore
	if(src instanceof TargetClass){
		return src as Ty.InstanceType_<Target>
	}else{
		if( typeof errMsg === 'string' ){
			throw new Error(errMsg)
		}else{
			throw errMsg
		}
	}
}



/**
 * 基本數據類型 類型斷言 帶運行時檢查
 * let a:any = 1
 * let b = primitiveAs(a, 'number') //b:number
 * 
 * @param src 
 * @param target 
 * @param errMsg 
 * @returns 
 */

export function primitiveAs<Target extends string>(src, target:Ty.PrimitiveTypeStr|Target, errMsg:any=''){
	if(typeof src === target){
		return src as Ty.ParseType<Target>
	}else{
		if( typeof errMsg === 'string'){
			throw new Error(errMsg)
		}else{
			throw errMsg
		}
	}
}

/** primitiveAs */
export function As<Target extends jstype>(src, target:Target, errMsg?:any):Ty.ParseType<Target>

/** instanceAs */
export function As<Target extends { prototype: any }>(src, target:Target, errMsg?:any):Ty.InstanceType_<Target>

export function As<Target extends string|{ prototype: any }>(src, target:Target, errMsg:any=''){
	if(typeof target === 'string'){
		return primitiveAs(src, target, errMsg)
	}else{
		return instanceAs(src, target, errMsg)
	}
}
