<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/man.png" width="100px" height="100px">
    <!--    <img alt="Vue logo" src="./assets/logo.png">-->
    <!--    <UsersList msg="My Training APP"/>-->
    <h1>My-Training</h1>
    <hr>
<!--    <router-link to="/">Home</router-link> <router-link to="/users">UsersList</router-link> -->
    <h1></h1>
    <transition name="fade" mode="out-in">
      <router-view></router-view>
    </transition>
  </div>
</template>

<!--<script>-->
<!--  import UsersList from './components/UsersList.vue'-->
<!--  export default {-->
<!--    name: 'app',-->
<!--    components: {-->
<!--      UsersList-->
<!--    }-->
<!--  }-->
<!--</script>-->

<script>

  export default {
    data() {
      return {}
    }
  }
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
  html,
  body{
    background: lightcyan;
  }

  .fade-enter-active, .fade-leave-active {
    transition: opacity .3s ease-out;
  }
  .fade-enter, .fade-leave-to {
    opacity: 0;
  }

</style>