export class Live{
	province:str
	city:str
	adcode:str
	weather:str
	temperature:str
	winddirection:str
	windpower:str
	humidity:str
	reporttime:str
	temperature_float:str
	humidity_float:str
}

export class Weather{
	status:str
	count:str
	info:str
	infocode:str
	lives: Live[]
}