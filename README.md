### two-endpoints


This project has two endpoints for managing payment links: _parse-link_ and _create-link_.


**server port is 8081**

Endpoints:


**POST /parse-link**  
`http://localhost:8081/parse-link`  
_json payload_:  
`
{
"paymentLink": "link"
}
`

**POST /create-link**  
`http://localhost:8081/create-link`

_json payload_ :  
`
{
"standardVersion": "",
"typeOfPaymentLink": "",
"informationAboutServiceProvider": "",
"mcc": "",
"currencyCode": "",
"paymentAmount": "",
"providerName": "",
"dataChecksum": ""
}
`

example:   
`
{
"standardVersion": "01",
"typeOfPaymentLink": "11",
"informationAboutServiceProvider": "320010balance.kg100512345120211130211-330003521",
"mcc": "6012",
"currencyCode": "417",
"paymentAmount": "56100",
"providerName": "balance.kg",
"dataChecksum": "d271"
}
`
