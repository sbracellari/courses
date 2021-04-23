<c:set var="req" value="${pageContext.request}" />
<c:set var="urlBase" value="${req.scheme}://${req.serverName}:${req.localPort}${req.contextPath}" />
<script>
  var token  = '${bearer.getEncryptedToken()}'
  var is_demo = false
</script>
<link href='//fonts.googleapis.com/css?family=Arimo' rel='stylesheet' type='text/css'>
<div id="courses-soffit" style="margin: -10px;">
  An error occurred
</div>
<script src="${pageContext.request.contextPath}/js/main.js" type="text/javascript"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
