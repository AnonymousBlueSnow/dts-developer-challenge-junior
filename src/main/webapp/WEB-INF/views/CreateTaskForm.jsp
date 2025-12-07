<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML>
<html xmlns:form="http://www.w3.org/1999/html">

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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>

<body>

    <!-- Web header -->
    <nav class="navbar  container-fluid">
        <div class="row">
            <h3 class="webTitle ms-4 ">HMCTS Case System</h3>
        </div>
        <div class="w-100"></div>
        <div class="row">
            <button id="back" class=" btn btn-sm themeButtonInvert ms-4"
                onclick="window.location.href='/cases'">
                <div class="row g-1 align-items-center">
                    <div class="col">
                        Back
                    </div>
                </div>
            </button>
        </div>
    </nav>

    <!-- Title page -->
    <div class="container col-lg-6 col-sm-12 mb-4 ">
        <h2 class="text-center">Create a new Task for Case ${task.getParentCase().getCaseNumber()}</h2>
    </div>

    <!-- Task Form -->
    <div class="container card col-lg-6 col-sm-12 ">
        <form:form action="/createTask/${task.getParentCase().getCaseNumber()}" method="post" class="p-4"
            modelAttribute="task">
            <div class="form-group mb-4 ">
                <form:label class="form-label required" path="title">Please enter the task title</form:label>
                <form:input path="title" id="title" class="form-control" placeholder="Enter title" type="text"
                    required="true" maxlength="255" />
                <form:errors path="title" />
            </div>

            <div class="form-group mb-4">
                <form:label class="form-label" path="description">Please enter the task description
                </form:label>
                <form:textarea path="description" id="description" class="form-control"
                    placeholder="Enter description" type="text" maxlength="2000" />
                <form:errors path="description" />
            </div>

            <div class="form-group mb-4">
                <form:label class="form-label required" path="status">Please enter the task status</form:label>
                <form:input path="status" id="status" class="form-control" placeholder="Enter status"
                    type="text" required="true" maxlength="255" />
                <form:errors path="status" />
            </div>

            <div class="form-group mb-4 ">
                <form:label class="form-label required" path="dueDateTime">Please enter the due date and time
                </form:label>
                <form:input path="dueDateTime" id="dueDateTime" class="form-control"
                    placeholder="Enter dueDateTime" type="datetime-local" min="${currentDateTime}"
                    required="true" />
                <form:errors path="dueDateTime" />
            </div>

            <div class="d-flex justify-content-around ">
                <button type="submit" id="submit" class="btn themeButton">
                    <div class="row g-1 align-items-center">
                        <div class="col">
                            Submit
                        </div>
                    </div>
                </button>
            </div>
        </form:form>
    </div>
</body>