Toast={
  success:function (meesage){
    Swal.fire({
      position: 'top-end',
      icon:'success',
      title:meesage,
      showConfirmButton: false,
      timer: 3000
    });
  },
  error:function(meesage){
    Swal.fire({
      position: 'top-end',
      icon:'error',
      title:meesage,
      showConfirmButton: false,
      timer: 3000
    });
  },
  warning:function(meesage){
    Swal.fire({
      position: 'top-end',
      icon:'warning',
      title:meesage,
      showConfirmButton: false,
      timer: 3000
    });
  }
};