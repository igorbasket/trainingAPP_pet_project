<template>
    <div>
    <section id="app" class="hero is-warning is-fullheight is-bold">
        <div class="hero-body">
            <div class="container has-text-centered">

                <h2 class="title is-6">{{ message }}</h2>

                <!--  THE TIMER NUMBERS  -->
                <div id="timer">
                    <span id="minutes">{{ minutes | twoDigits }}</span>
                    <span id="middle">:</span>
                    <span id="seconds">{{ seconds | twoDigits }}</span>
                </div>
                <!--  THE BUTTONS  -->
                <div id="buttons">

                    <!--  START BUTTON    -->
                    <el-button type="success"
                            id="start"
                            class="button is-dark is-large"
                            @click='startTimer'
                            v-show="showContinue">start
<!--                        <i class="far fa-play-circle"></i>-->
                    </el-button>

                    <!--   PAUSE BUTTON   -->
                    <el-button type="primary"
                            id="stop"
                            class="button is-dark is-large"
                            @click="pauseTimer"
                            v-show='!showContinue'>pause
<!--                        <i class="far fa-pause-circle"></i>-->
                    </el-button>

                    <!--  RESET BUTTON   -->
                    <el-button type="warning"
                            id="reset"
                            class="button is-dark is-large"
                            @click="resetTimer"
                            v-show="showReset">reset
<!--                        <i class="fas fa-undo"></i>-->
                    </el-button>
                </div>
                <small class="tet-muted">DataProps: {{date}}</small>
              <div>
              <small class="tet-muted">DataCurrent: {{this.seconds}}</small>
              </div>

            </div>
        </div>
    </section>
        </div>
</template>

<script>
    const default_title = 'Let the countdown begin!!';

    export default {
        name: "CountdownTimer",
      //  el: '#app',
        props: {
            date: {
                type: Number
            },
            dataTimeout: {
                type: Number
            },
            trainingFinished:{
                type: Boolean
            }
        },
        data() {
            return {
                message: default_title,
                minutes: Math.floor(Number((this.date) / 60) % 60),
                seconds: Number(this.date % 60),
                interval: null,
                showContinue: true,
                showReset: false,
                trainingStopped: this.trainingFinished
            }
        },
        methods: {
            startTimer: function(){
                this.showContinue = false;
                    this.showReset = true;
                    this.message = 'Greatness is within sight!!';
                this.interval = setInterval(() => {
                    if (this.trainingFinished === true){
                      return this.pauseTimer()
                    }
                    if(this.seconds === 0){
                        this.seconds = 59;
                        this.minutes--;
                    }else{
                        this.seconds--;
                    }

                    if(this.minutes === 0 && this.seconds === 0){
                      console.log("00")
                        this.nextEx()
                    //  this.resetTimer()
                    }
                },1000)
            },
            nextEx: function(){
              this.$emit('next-ex')

            },
            pauseTimer: function(){
                this.showReset = true;
                this.showContinue = true;
                this.message = 'Never quit, keep going!!';
                clearInterval(this.interval);
            },
            resetTimer: function(){
                this.showContinue = true;
                this.showReset = true;
                this.minutes = Math.floor(Number(((this.date) / 60) % 60));
                this.seconds = Number((this.date) % 60);
                clearInterval(this.interval);
                this.message = default_title;

                console.log("reset pressed")

            }
        },
        watch: {
          date: function () {
             // this.message = default_title
              this.minutes = Math.floor(Number((this.date) / 60) % 60)
              this.seconds = Number(this.date % 60)
              this.trainingStopped = this.trainingFinished

            //  this.interval = null
             // this.showContinue = true
            //  this.showReset = false
        }
      },
        filters:{
            twoDigits: function(num){
                if(num<10){
                    return '0'+ num
                }else{
                    return num
                }
            }
        }
    }
</script>

<style scoped>
    #message {
        color: #DDD;
        font-size: 50px;
        margin-bottom: 20px;
    }

    #timer {
        font-size: 150px;
        line-height: 1;
        margin-bottom: 40px;
    }
</style>