// export {}

// class Time{
// 	value
// 	toInt(){
// 		return this.value
// 	}
// 	static fromInt(int:int){
// 		const t = new Time()
// 		t.value = int
// 		return t
// 	}
	
// }

// type Fun<P extends any[]=any[], R=any> = (...args:P)=>R

// class BaseFact{
// 	// mk:Fun
// 	// fromRow:Fun
// 	// correctRow:Fun
// 	// correctObj:Fun
// }

// class BaseInst{
// 	//toRow:Fun
	
// }

// class BaseRow{

// }

// function assign(a,b){
// 	return Object.assign(a,b)
// }

// class Mk<Obj, Row, Inst>{
// 	protected constructor(){}
// 	protected __init__(...args: Parameters<typeof Mk.new>){
// 		const z = this
// 		return z
// 	}

// 	static new<Obj, Row, Inst>(){
// 		const z = new this<Obj, Row, Inst>()
// 		z.__init__()
// 		return z
// 	}

// 	get This(){return Mk}

// 	Fact = BaseFact
// 	Inst = BaseInst
// 	Row = BaseRow

// 	mkFactory(){
// 		const z = this
// 		const Inst = z.Inst
// 		const Fact = class extends BaseFact{
// 			mk(prop:Obj){
// 				const ans = new Inst()
// 				assign(ans, prop)
// 				return ans
// 			}
// 			correctRow(row:Row){
// 				return z.correctRow(row)
// 			}
// 			fromRow(row:Row):Inst{
// 				let ans = new Inst()
// 				//@ts-ignore
// 				ans = z.correctObj(ans)
// 				return ans as Inst
// 			}
// 		}
// 		z.Fact = Fact
// 		return Fact
// 	}
// 	mkInst(){
// 		const z = this
// 		const Row = z.Row
// 		const Inst = class extends BaseInst{
// 			toRow():Row{
// 				let ans = new Row()
// 				//@ts-ignore
// 				ans = z.correctRow(ans)
// 				return ans as Row
// 			}
// 		}
// 		z.Inst = Inst
// 		return Inst
// 	}

// 	correctRow:(row:Row)=>Row = (row)=>row
// 	correctObj:(obj:Inst)=>Inst = (obj)=>obj
// }

// class Row{

// }

// class Obj{

// }

// class WordRow{
// 	id:int
// 	text:str
// 	ct:int
// }

// class WordObj{
// 	id:int
// 	text:str
// 	ct:Time
// }


// const wordMk = Mk.new<WordObj, WordRow, WordObj>()
// const WordFact = wordMk.mkFactory()
// const WordInst = wordMk.mkInst()
