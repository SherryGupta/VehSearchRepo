# new feature
# Tags: optional
    
Feature: Search Car

Scenario: search car by car reg number for all car details stored in csv/xsl file in a given direcotry
Given csv/xsl files with car details exists in folder
When service is invoked to read the file
And user navigates to DVLA site
And search for cars by car reg number received in from service
Then cars details displayed on the website and matched with expected car details
  |CK58 AEY|FORD|SILVER|
|ST57 KCC|VAUXHALL|RED|



