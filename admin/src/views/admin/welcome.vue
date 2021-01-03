<template>
  <div>
    <h1>您好，欢迎进入控台</h1>
  </div>
</template>

<script>

export default {
  name: 'welcome',
  mounted:function(){
    //sidebar 激活样式方法
  //  this.$parent.activeSidebar("welcome-sidebar");
   },
  methods:{
    login(){
      this.$router.push("/admin")
    }
  }
}
</script>
