<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
        crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css" />
    <script src="https://cdn.lordicon.com/lordicon.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

    <!-- Search bar script -->
    <script>
        function searchFunction(searchString) {
            var caseItems = document.querySelectorAll('.caseItem');

            // If empty, all items show
            if (!searchString) { 
                for (var i = 0; i < caseItems.length; i++) {
                    caseItems[i].style.display = "block";
                }
                return;
            }

            //Checks for matching terms, disappears the case from view if there is no match
            for (var i = 0; i < caseItems.length; i++) {
                var caseItem = caseItems[i];
                var caseTitle = $(caseItem).find('.caseTitle').text();
                console.log(caseTitle);
                var caseDescription = $(caseItem).find('.caseDescription').text();
                console.log(caseDescription);
                var caseNumber = $(caseItem).find('.caseNumber').text();
                console.log(caseNumber);

                if (caseTitle.toLowerCase().includes(searchString.toLowerCase()) || caseDescription.toLowerCase().includes(searchString.toLowerCase()) || caseNumber.toLowerCase().includes(searchString.toLowerCase())) {
                    caseItem.style.display = "block";
                } else {
                    caseItem.style.display = "none";
                }
            }
        }
    </script>
    </head>

    <body>

        <!-- Web header -->
        <nav class="navbar">
            <h3 class="webTitle ms-4">HMCTS Case System</h3>
        </nav>

        <!-- Search bar -->
        <form class="container col-lg-6 col-sm-12 mb-4 ">
            <h2 class="text-center"> All Cases</h2>
            <input class="form-control" type="search" placeholder="Search for a case" aria-label="Search"
                onkeyup="searchFunction(this.value)" oninput="searchFunction(this.value)">
        </form>

        <!-- Displays all cases with the option to view specific case tasks, or create a new task for a case -->
        <c:forEach var="c" items="${Cases}">
            <div class="container card col-lg-6 col-sm-12 shadow-sm mt-3 mb-3 p-0 caseItem">
                <div class="card-header caseNumber d-flex justify-content-between align-items-center">
                    <div>${c.caseNumber} - ${c.status}</div>
                    <div>${c.createdDate.toString().substring(0,10)}</div>
                </div>
                <div class="card-body">
                    <h5 class="card-title caseTitle">${c.title}</h5>
                    <p class="caseDescription">${c.description}</p>
                </div>
                <div class="container row d-flex justify-content-around mb-2">
                    <button class=" themeButton col-4 btn d-flex align-items-center justify-content-around"
                        onclick="window.location.href='/cases/${c.caseNumber}'">
                        <lord-icon src="https://cdn.lordicon.com/hmpomorl.json" trigger="hover" stroke="bold"
                            colors="primary:#000000,secondary:#1d70b8">
                        </lord-icon>
                        <span> View Tasks</span>
                    </button>

                    <button class=" themeButton col-4 btn d-flex align-items-center justify-content-around"
                        onclick="window.location.href='/createTask/${c.caseNumber}'">
                        <lord-icon src="https://cdn.lordicon.com/vjgknpfx.json" trigger="hover" stroke="bold"
                            colors="primary:#000000,secondary:#1d70b8">
                        </lord-icon>
                        <span>Create a new task</span>
                    </button>

                </div>
            </div>
            </div>
        </c:forEach>

    </body>