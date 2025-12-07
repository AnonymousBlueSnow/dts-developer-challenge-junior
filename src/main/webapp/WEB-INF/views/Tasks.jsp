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
    integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css" />
</head>


<body>

  <!-- Web Header -->
  <nav class="navbar  container-fluid">
    <div class="row">
      <h3 class="webTitle ms-4 ">HMCTS Case System</h3>
    </div>
    <div class="w-100"></div>
    <div class="row">
      <button id="back" class=" btn btn-sm themeButtonInvert ms-4" onclick="window.location.href='/cases'">
        <div class="row g-1 align-items-center">
          <div class="col">
            Back
          </div>
        </div>
      </button>
    </div>
  </nav>

  <!-- Displays list of tasks for a specific case -->
  <h2 class="text-center">Tasks for ${Case.title} (${Case.caseNumber}) </h2>
    <c:forEach var="task" items="${Tasks}">

      <!-- Checks if new task has been created, if so, then display the success message -->
      <div class="container  col-lg-6 col-sm-12 shadow-sm mt-3 p-0">
      <c:if test="${not empty newTaskId}">
        <div id="success-${task.id}" class="alert alert-success mb-2 mt-4" role="alert" style="display: none">
          Task successfully created!
        </div>
      </c:if>
      </div>

      <div class="container card col-lg-6 col-sm-12 shadow-sm mt-3 p-0">
        <div class="card-header d-flex justify-content-between align-items-center">
          <div>${task.status}</div>
          <div>Due: ${task.dueDateTime.toString().substring(0,10)}</div>
        </div>
        <div class="card-body">
          <h5 class="card-title">
            ${task.title}
          </h5>
          <p>
            ${task.description}
          </p>
        </div>

        <div hidden id="${task.id}"></div>
      </div>
    </c:forEach>

<script>
  const newTaskId = '${newTaskId}';
  if (newTaskId && newTaskId !== '') {
    const successBox = document.getElementById("success-" + newTaskId);

    if (successBox) {
      successBox.style.display = "block";
      window.scrollTo({ top: document.body.scrollHeight, behavior: 'smooth' });
    }
  }
</script>
</body>
