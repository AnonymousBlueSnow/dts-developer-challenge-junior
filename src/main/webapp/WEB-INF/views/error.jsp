<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css"  />
</head>
<body>

<!--Web header  -->
<nav class="navbar container-fluid">
  <div class="row">
    <h3 class = "webTitle ms-4 ">HMCTS Case System</h3>
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

<!-- Error code -->
<h2 class="text-center">Oops, an error has occurred</h2>
  <div class="container card col-lg-6 col-sm-12 shadow-sm mt-3 p-0">
    <div class="card-body d-flex justify-content-around align-items-center" >
      <p> ${status} </p>
      <p> ${error}</p>
    </div>
  </div>

</body>
