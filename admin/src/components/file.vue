<template>
  <div  >
    <button type="button" v-on:click="selectFile()" class="btn btn-white btn-default btn-round">
      <i class="ace-icon fa fa-upload"></i>
      {{ text }}
    </button>
    <input hidden
           ref="file" v-bind:id="inputId+'-input'" type="file" v-on:change="uploadFile()">
  </div>
</template>

<script>
export default {
  name: 'file',
  props: {
    text:{
      default: "上传文件"
    },
    inputId:{
      default: "file-upload"
    },
    suffixs:{
      default:[]
    },
    use:{
      default:''
    },
    list: {
      type: Function,
      default: null
    },
    afterUpload:{
      type:Function,
      default:null
    },
    itemCount: Number // 显示的页码数，比如总共有100页，只显示10页，其它用省略号表示
  },
  data: function () {
    return { }
  },
  methods: {

    uploadFile() {
      let _this = this;
      let formData = new window.FormData();
      let file = _this.$refs.file.files[0];

      let suffixs = _this.suffixs;//['jpg', 'jpeg', 'png'];
      let fileName=file.name;
      let suffix=fileName.substring(
          fileName.lastIndexOf(".")+1,
          fileName.length
      ).toLowerCase();

      let validateSuffix=false;

      for(let i=0;i<suffix.length;i++){
        if(suffixs[i].toLowerCase()===suffix){
          validateSuffix=true;
          break;
        }
      }
      if(!validateSuffix){
        Toast.warning("文件格式不正确！只支持上传："+suffixs.join(","))
        $('#'+_this.inputId+"-input").val('');
        return;
      }

      formData.append('file',file);
      formData.append("use",_this.use);
     /* Loading.show();*/
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/upload',
          formData).then((response)=> {
       /* Loading.hide();*/
        let resp = response.data;
        _this.afterUpload(resp);
        $('#'+_this.inputId+"-input").val('');
      });
    },
    selectFile() {  let _this = this;
      $('#'+_this.inputId+"-input").trigger('click');
    }
  }
}
</script>
