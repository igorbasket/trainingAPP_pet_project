<template>
    <div>
        <small class="tet-muted">Data: {{this.myTrain}}</small>
<!--        <CountdownTimer class="time" :date="exercise.time" :data-timeout="exercise.timeout"></CountdownTimer>-->

<!--        <el-carousel :interval="5000" arrow="always">-->
<!--            <el-carousel-item v-for="elem in this.myTrain" :key="elem.message">-->
<!--                <h3>{{ elem.name }}</h3>-->
<!--            </el-carousel-item>-->
<!--        </el-carousel>-->

<!--        <li v-for="elem in exercise.items" :key="elem.message">-->
<!--            {{elem.message}}-->
<!--        </li>-->
<!--        <div>-->
<!--            {{this.exercise.displayEx}}-->
<!--        </div>-->

        <CountdownTimer v-if="this.myTrain !== null"
            v-bind:date=this.currentExerciseTime
                        v-bind:trainingFinished="this.trainingFinished"
            @next-ex="nextEx"></CountdownTimer>
<!--      <small class="tet-muted">Data: {{this.myTrain[this.currentExerciseItem].time}}</small>-->
    </div>
</template>

<script>
     // import CountdownTimer from './Timer.vue'
    import CountdownTimer from './Timer3.vue'

    export default {
        name: "TrainingDisplay",
        components: {
            CountdownTimer,
        },
        props: ['trName'],
        data() {
            return {
                exercise: {
                    time: '12',
                    timeout: '10.00',
                    items: [
                        { message: 'Exercise 1' },
                        { message: 'Exercise 2' },
                        { message: 'Exercise 3' },
                        { message: 'Exercise 4' },
                        { message: 'Exercise 5' }
                ],
                    displayEx: 10000
                },
                myTrain: null,
                currentExerciseTime: 15,
                currentExerciseItem: 1,
                trainingFinished: false


            };
        },
        methods: {
            nextEx(){
              if(this.currentExerciseItem === this.myTrain.length-1){
                return this.trainingFinished = true
              }

              console.log("Tr stop = "+this.trainingFinished)
              this.currentExerciseItem++
              this.currentExerciseTime = this.myTrain[this.currentExerciseItem].time
              if(this.currentExerciseTime === 0){
                this.nextEx()
              }
              console.log("nextEx pressed")
              console.log(this.currentExerciseItem)
            },
            exerciseArray () {
                let current = 0;
                // setTimeout(this.exerciseArray, 1000)

                for (let i=0; i<this.exercise.items.length; i++) {
                    this.exercise.displayEx = this.exercise.items[i];
                }

                this.exercise.displayEx = this.exercise.items[current];
                current++;
                // if(current >= this.exercise.items.length) current = 0;


                // setInterval(function(){
                //     this.displayEx = this.items[current];
                //     current++;
                //     if(current >= this.items.length) current = 0;
                // }, 2000);

            },
            currentDate () {
            // Использовать чтобы установить дату последнего использования тренировки, ставить после окончания тренировки
            // var today = new Date();
            // var dd = String(today.getDate()).padStart(2, '0');
            // var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
            // var yyyy = today.getFullYear();
            //
            // today = mm + '/' + dd + '/' + yyyy;
            // document.write(today);
          }

        },
      mounted: function () {
        // this.exerciseArray()


        this.$http.get('api/training/' + this.trName + '/')
            .then(response => {
              this.myTrain = response.data
              this.currentExerciseTime =  this.myTrain[1].time
              if(this.currentExerciseTime === 0){
                this.currentExerciseTime = 1
              }
              console.log(this.currentExerciseTime)
            })
            .catch(response => {
              //debugger
              alert(response.status)
            })

        console.log("mounted finished")

        // this.items.forEach(function(item){
        //     copy.push(item)
        // });
      },
    }
</script>

<style scoped>
    /*.el-carousel__item h3 {*/
    /*    color: #475669;*/
    /*    font-size: 70px;*/
    /*    opacity: 0.75;*/
    /*    line-height: 300px;*/
    /*    margin: 0;*/
    /*}*/

    /*.el-carousel__item:nth-child(2n) {*/
    /*    background-color: #DDDDDD;*/
    /*}*/

    /*.el-carousel__item:nth-child(2n+1) {*/
    /*    background-color: antiquewhite;*/
    /*}*/
</style>
