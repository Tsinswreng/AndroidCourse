# AndroidCourse



## /user下:


### 登录:

<table>
	<tr>
		<td>url</td><td>请求方法</td><td>功能</td>
	</tr>
	<tr>
		<td>/login</td><td>post</td><td>登录</td>
	</tr>
</table>

请求参数详解:

<table>
	<tr>
		<td>字段名</td><td>类型</td><td>解释</td>
	</tr>
	<tr>
		<td>name</td><td>字符串</td><td>用户名</td>
	</tr>
<tr>
		<td>pswd</td><td>字符串</td><td>密码</td>
	</tr>
</table>

<!-- 字段名	类型	解释
name	字符串	用户名
pswd	字符串	密码 -->

**响应:** 

成功: 状态码200, 返回用户id字符串

失败: 状态码403, 返回"failed"


### 注册:

<table>
	<tr>
		<td>url</td><td>请求方法</td><td>功能</td>
	</tr>
	<tr>
		<td>/signUp</td><td>post</td><td>注册</td>
	</tr>
</table>

请求参数和响应同/login

### 天气:

<table>
	<tr>
		<td>url</td><td>请求方法</td><td>功能</td>
	</tr>
	<tr>
		<td>/weather</td><td>get</td><td>获取北京东城区的天气</td>
	</tr>
</table>
请求参数: 无
响应: 如下

```json
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
```

## /article 下:

### 获取所有文章

<table>
	<tr>
		<td>url</td><td>请求方法</td><td>功能</td>
	</tr>
	<tr>
		<td>/allArticle</td><td>get</td><td>获取所有文章</td>
	</tr>
</table>

请求参数: 无

响应: json格式的Article数组。Article简单对象的结构定义见前文


### 添加评论

<table>
	<tr>
		<td>url</td><td>请求方法</td><td>功能</td>
	</tr>
	<tr>
		<td>/addComment</td><td>post</td><td>添加评论</td>
	</tr>
</table>

请求参数:

<table>
	<tr>
		<td>字段名</td><td>类型</td><td>解释</td>
	</tr>
	<tr>
		<td>user_id</td><td>整数</td><td>用户id</td>
	</tr>
	<tr>
		<td>article_id</td><td>整数</td><td>文章id</td>
	</tr>
	<tr>
		<td>text</td><td>字符串</td><td>评论内容</td>
	</tr>
	<tr>
		<td>score</td><td>整数</td><td>评分</td>
	</tr>
</table>

**响应参数:** 

成功:状态码200, 返回空字符串

失败:状态码400, 返回"failed"


### 通过文章id查询评论:


<table>
	<tr>
		<td>url</td><td>请求方法</td><td>功能</td>
	</tr>
	<tr>
		<td>/seekComments</td><td>get</td><td>查找评论</td>
	</tr>
</table>

请求参数:

<table>
	<tr>
		<td>字段名</td><td>类型</td><td>解释</td>
	</tr>
	<tr>
		<td>article_id</td><td>整数</td><td>文章id</td>
	</tr>
</table>

**响应参数:** 

成功:状态码200, 返回json格式的Comment简单对象数组

失败:状态码400, 返回"failed"