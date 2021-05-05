<template>
    <div align="center">
        <i style="font-size: large">Editing a training:</i>
        <h1/>
        <el-form :model="newTrainingForm" ref="newTrainingForm" class="demo-dynamic" style="width: 400px" >
            <el-form-item label="Training name:"
                          :rules="{message: 'Training can not be null', trigger: 'blur'}">
                <i>{{this.trName}}</i>
            </el-form-item>

            <el-form-item v-show="showBtn" label="Input rest duration">
                <!--        <strong>Rest duration</strong>-->
                <el-tooltip class="item" effect="dark" content="Input minutes" placement="top">
                    <el-input type="number" min="0" max="59" class="inputEx" v-model="valueMinutesRest" style="width: 80px"/>
                </el-tooltip>
                <span class="mid" id="middlee">:</span>
                <el-tooltip class="item" effect="dark" content="Input seconds" placement="top">
                    <el-input type="number" min="0" max="59" class="inputEx" v-model="valueSecondsRest" style="width: 80px"/>
                </el-tooltip>
            </el-form-item>


            <el-form-item
                    v-for="(domain, index) in newTrainingForm.listExercise"
                    :key="domain.key"
                    :rules="{
      required: true, message: 'Exercise can not be empty', trigger: 'blur'
    }"
            >
                <strong>Exercise {{index + 1}}</strong>
                <el-input class="inputEx" placeholder="Input exercise name" v-model="domain.name"/>
                <el-input type="textarea" placeholder="Input exercise description" v-model="domain.description"/>

                <!--            <el-time-picker placeholder="Pick exercise duration" v-model="domain.timeEx" style="width: 100%;"/>-->
                <div class="inputSecondsArea">
                    <el-tooltip class="item" effect="dark" content="Input minutes" placement="top">
                        <el-input type="number" min="0" max="59" class="inputEx" v-model="domain.valueMinutes" style="width: 80px"/>
                    </el-tooltip>
                    <span class="mid" id="middle">:</span>
                    <el-tooltip class="item" effect="dark" content="Input seconds" placement="top">
                        <el-input type="number" min="0" max="59" class="inputEx" v-model="domain.valueSeconds" style="width: 80px"/>
                    </el-tooltip>
                </div>

                <!--            <el-input type="time" v-model="domain.timeEx"></el-input>-->
                <el-button class="del" type="danger" @click.prevent="removeDomain(domain)">Delete</el-button>
            </el-form-item>
            <el-button v-show="showBtnEx" type="primary" @click="showEx">Show exercises</el-button>
            <el-form-item>
                <el-button v-show="showBtn" type="success" @click="submitForm('newTrainingForm')">Edit training</el-button>
                <el-button v-show="showBtn" type="primary" @click="addDomain('newTrainingForm')">Add exercise</el-button>
                <!--            <el-button @click="resetForm('newTrainingForm')">Reset</el-button>-->
            </el-form-item>
            <small class="tet-muted">Data: {{this.myTrain}}</small>
        </el-form>


    </div>
</template>


<script>

    export default {
        name: "EditTraining",
        props: ['trName'],
        data() {
            return {
                newTrainingForm: {
                    name: '',
                    email: 'igorbasket93@gmail.com',
                    listExercise: []
                },
                listExerciseDto: [],
                time: 0,
                valueMinutesRest: "00",
                valueSecondsRest: "00",
                index: 0,
                train: Object,
                showBtn: false,
                showBtnEx: true,


                myTrain: null,
                myTrain2: null
            };
        },
        methods: {
            submitForm(formName) {

                this.$refs[formName].validate((valid) => {
                    if (valid) {

                        let sum = (this.valueMinutesRest * 60) + this.valueSecondsRest;
                        let add = {name: 'rest', description: 'rest', time: sum};
                        this.listExerciseDto.push(add);

                       // this.createListEx();
                        let i = 0;
                        while (i < this.newTrainingForm.listExercise.length){
                            let sum = (this.newTrainingForm.listExercise[i].valueMinutes * 60) + this.newTrainingForm.listExercise[i].valueSeconds;
                            let add = {name: this.newTrainingForm.listExercise[i].name,
                                description: this.newTrainingForm.listExercise[i].description,
                                time: sum};
                            this.listExerciseDto.push(add);
                            i++
                        }

                        this.$http.post('api/training/updateTraining/',{
                            name: this.newTrainingForm.name,
                            email: this.newTrainingForm.email,
                            listExerciseDto: this.listExerciseDto,
                        }).then(response => {
                            let a = response.data;
                            this.train = a
                        });
                        alert('Training was edited');
                        this.$router.push('/user_training_list')
                //        this.$router.push('/user_training_list')
                    } else {
                        alert('error submit!!');
                        return false;
                    }
                });

            },
            // resetForm() {
            //     this.newTrainingForm.listExerciseDto.timeEx = 0;
            //     this.newTrainingForm.listExerciseDto.nameEx = '';
            //     this.newTrainingForm.name = '';
            // },
            resetForm(formName) {
                this.$refs[formName].resetFields();

            },
            removeDomain(item) {
                let index = this.newTrainingForm.listExercise.indexOf(item);
                if (index !== -1) {
                    this.newTrainingForm.listExercise.splice(index, 1);
                }
                this.index--
            },
            showEx(){
                let i = 1;
                while (i < this.myTrain.length) {
                    let min = Math.floor(Number(this.myTrain[i].time / 60) % 60);
                    let sec = Number(this.myTrain[i].time % 60)
                    let add = {
                        name: this.myTrain[i].name,
                        description: this.myTrain[i].description,
                        valueMinutes: min, valueSeconds: sec
                    };
                    this.newTrainingForm.listExercise.push(add);
                    i++
                }
                this.showBtn = true;
                this.showBtnEx = false

                let min = Math.floor(Number(this.myTrain[0].time / 60) % 60);
                let sec = Number(this.myTrain[0].time % 60)
                this.valueMinutesRest = min;
                this.valueSecondsRest = sec
            },
            addDomain() {

                this.index++;
                this.newTrainingForm.listExercise.push({
                    name: '',
                    description: '',
                    valueMinutes: "00",
                    valueSeconds: "00",
                });
                }
            },
            createListEx(){
                let i = 0;
                while (i < this.newTrainingForm.listExercise.length){
                    let sum = (this.newTrainingForm.listExercise[i].valueMinutes * 60) + this.newTrainingForm.listExercise[i].valueSeconds;
                    let add = {name: this.newTrainingForm.listExercise[i].name,
                        description: this.newTrainingForm.listExercise[i].description,
                        time: sum};
                    this.listExerciseDto.push(add);
                    i++
                }
        },
        computed:{
            sum() {
                return this.newTrainingForm.listExercise.valueMinutes * 60
            }
        },
        mounted() {
            this.$http.get('api/training/' + this.trName +'/')
                .then(response => {
                    this.myTrain = response.data
                })
                .catch(response => {
                    //debugger
                    alert(response.status)
                })

            this.newTrainingForm.name = this.trName

        },
    }
</script>

<style scoped>

    .del{
        margin-top: 1rem;
    }

    .mid{
        font-weight: bolder;
    }
    strong{
        color: rgba(12,12,12,0.68);
        float: left
    }

    .inputEx{
        margin-bottom: 10px;
    }
    .inputSecondsArea{
        margin-top: 10px;
    }

</style>