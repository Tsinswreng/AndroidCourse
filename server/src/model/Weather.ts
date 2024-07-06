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

/* 

{
	"status": "1",
	"count": "1",
	"info": "OK",
	"infocode": "10000",
	"lives": [
		{
			"province": "北京",
			"city": "东城区",
			"adcode": "110101",
			"weather": "阴",
			"temperature": "24",
			"winddirection": "南",
			"windpower": "≤3",
			"humidity": "79",
			"reporttime": "2024-07-01 21:03:17",
			"temperature_float": "24.0",
			"humidity_float": "79.0"
		}
	]
}

*/