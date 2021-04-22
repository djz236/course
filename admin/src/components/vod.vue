<template>
  <big-file
      v-bind:input-id="inputId"
      v-bind:text="text"
      v-bind:suffixs="suffixs"
      v-bind:use="use"
      v-bind:after-upload="afterUpload"
      v-bind:shard-size="1000*1024*1024"
      v-bind:url="'vod'"
  ></big-file>
</template>
<script>
import BigFile from "@/components/big-file";

export default {
  components: {BigFile},
  name:'vod',
  props:{
    text:{
      default: "上传大文件"
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

  }

}
</script>