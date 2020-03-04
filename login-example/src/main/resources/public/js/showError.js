$(document).ready(function () {
  const params = new URLSearchParams(window.location.search);
  const error = params.get('error');
  if (error) {
    $("#alert-container").html(
      '<div class="alert alert-danger text-center">' +
         error +
      '</div>'
    );
  }
});