# HMCTS Dev Test - AnonymousBlueSnow
- In the repo other than the code, there is also a folder called "webPhotos" where I have attached images of the web application.

# Running the application steps
- Download/pull the contents in the repo to your machine.
- Open the application in Intellij IDEA (I developed in 2025.3 Community Edition).
- Please download MySQL Workbench if you don't have it.
- Create a database in there called "hmcts".
- Go to the application, and navigate to src -> main -> resources -> application.properties and update lines 6,7 and 8 with your MySQL Workbench database details.
- Run the application by going to src -> main -> java ->uk.gov.hmcts.reform.dev -> Application, and run from there (click on the green run button on top right).
  Please wait to visit the below url until the "Run" output window shows "Reached end of dummy code creation"
- If the build or tests fail, just click to restart it as it always fixes the issue
- You can access the main solution by navigating to "http://localhost:8080/cases"
- You can access the swagger api interface by navigating to "http://localhost:8080/swagger-ui/index.html"
- You can run the unit/integration tests by going to src -> test and right clicking on the "test" folder, then selecting "Run Tests in Civil_Service.test"



