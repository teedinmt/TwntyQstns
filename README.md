# TwntyQstns

PROBLEM: Staff members have to spend extra time processing paper applications and handwritten forms  
SOLUTION: Have clients type in their own information, where it is then saved to a database, and printed on the PDF application form.


------------------------------------------------------------
This is my contribution to open-source code, a slight modification of a school project. I am planning for the next version! Thus, I would welcome feedback and suggestions about improvements, corrections, and ways to improve it.

If you customize and use it for an organization, I would love to hear about it! 

To understand this program, read the "Description of Program" section below.

To use this program, follow the steps in the "Customize and Install" file.

Want to participate? --see the "TODO" file


----------------------------------------------------------
                     DESCRIPTION OF PROGRAM
----------------------------------------------------------
WHAT TWENTY QUESTIONS APPLICATION DOES: 
 It takes in data from a client, saves it to a database, then also prints it to a pre-made PDF and saves the PDF file base on person's name and current date.
 The program uses 4 Screens: 
1)a welcome screen while checking database connection 
2)a screen collecting name, birthdate, general address info 
3)a screen collecting educational and employment info 
4)a customizable screen for any additional 7 questions. 

WHO COULD USE IT:
Anyone who needs clients to fill out an application could use this program, after configuring their fillable PDF form. Once set up and installed on a windows machine, both clients and staff use the same instance of the program. Staff login is required since it gives access to all client reports. 

HOW IT WORKS:
It is a stand-alone Java program for the Windows platform. Once installed, a client chooses "New Application" OR provides a staff username and password to log in. 
Data is saved to a relational MS Access database, split into front-end, back-end as MS Access recommends.
Program saves a PDF of intake data right away into a folder, using cleint's name and current date as the title for the file. 
Reports are available from the Staff login:  client PDF,  spreadsheet of all client info, spreadsheet of names and phone numbers
The database, client PDFs, and reports are saved to a secure location on a network.

SECURITY OF DATA:
Intake data is secured based on where the database is located. Client entering data does not have a way to access data through the program, however the program must have access to the location of the database. The LocalCustomization class sets the pathways for saving and accessing data. 

TOOLS & RESOURCES USED IN THIS PROJECT:
>>NetBeans, JavaFX, Scene Builder
>>Microsoft Access -- for database
>>jdbc:ucanaccess  -- for database access, see http://ucanaccess.sourceforge.net/site.html
>>Apache PDF Box, a Java library for creating PDFs, see https://pdfbox.apache.org/
>>Microsoft Word for creating original PDF form
>>PDFescape, a free tool for adding fillable fields to PDF, see https://www.pdfescape.com/windows/
>>Apache POI, an API for Microsoft Documents, used for creating Excel reports, see https://poi.apache.org/



