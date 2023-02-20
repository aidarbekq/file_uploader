# file_uploader

**server port is 4041**  
  
  


**POST`http://localhost:4401/in/{app_name}/{account_id}`**  
app_name - **must be** _mobile_,_web_ or _admin_  


while posting in the request header, set the "Content-Type" header to "multipart/form-data" by clicking on the "Headers" tab below the request URL field, and then adding a new header with the key "Content-Type" and the value "multipart/form-data".  
Then in form-data set to key "file" and for for value select file.  


**file max size is 20MB**


![image](https://user-images.githubusercontent.com/92877850/220053909-5062d03d-076f-41e9-92be-62f3a807163b.png)
