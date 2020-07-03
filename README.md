
# Twenty Questions Software
```
PROBLEM: Staff members have to spend extra time processing paper applications and handwritten forms  
SOLUTION: Have clients type in their own information, where it is then saved to a database, 
and then inserted into a fillable PDF form and saved to a reports folder.
```
This is my contribution to open-source code, a slight modification of a school project. I am planning for the next version! Thus, I would welcome feedback and suggestions about improvements, corrections, and ways to improve it.

If you customize and use it for an organization, I would love to hear about it! 


## Getting Started
To use this program, follow the steps in the "Customize and Install" file.

Twenty Questions is a stand-alone Java program for the Windows platform. Once installed, a client chooses "New Application" OR provides a staff username and password to log in. 
Data is saved to a relational Microsoft Access database, split into front-end, back-end as MS Access recommends. Reports are available from the Staff login:  client PDF,  spreadsheet of all client info, spreadsheet of names and phone numbers.  The database, client PDFs, and reports are saved to a secure location on a network.

### How it Works
 ```
 It takes in data from a client, saves it to a database, 
 then also prints it to a pre-made PDF and 
 saves the PDF file based on person's name and current date.

 The program uses 4 Screens: 
1)a welcome screen while checking database connection 
2)a screen collecting name, birthdate, general address info 
3)a screen collecting educational and employment info 
4)a customizable screen for any additional 7 questions.

```
## Deployment
```
See "Customize and Install" file. Basically, you will 
1)choose location for supporting files (database file and PDF fillable form), 
2)re-link the Microsoft Access databases 
3)create a distributable jar file, and save it to the computers you plan to use. 
(The program checks path to location of files, checks database when program starts.) 
```

## Tools used in this project:
* NetBeans, JavaFX, Scene Builder
* Microsoft Access -- for database
* [jdbc:ucanaccess](http://ucanaccess.sourceforge.net/site.html) - for database access 
* [Apache PDF Box](https://pdfbox.apache.org/) - a Java library for creating PDFs
* Microsoft Word - for creating original PDF form
* [PDFescape](https://www.pdfescape.com/windows/) - a free tool for adding fillable fields to PDF
* [Apache POI](https://poi.apache.org/) - an API for Microsoft Documents, used for creating 

## Author

* **Melinda Teed** - *Initial work* - [teedinmt](https://github.com/teedinmt)




