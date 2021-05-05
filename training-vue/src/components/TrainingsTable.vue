<template>
    <div align="center">
    <el-table
            :data="userTrainings"
            style="width: 100%"
            max-height="100%"
        >
        <el-table-column
                prop="name"
                label="Name"
                width="180">
        </el-table-column>
        <el-table-column
                prop="trainingDuration"
                label="Training duration"
                width="180">
        </el-table-column>
        <el-table-column
                prop="data"
                label="Last use date"
                width="180">
        </el-table-column>
        <el-table-column
                label="Operation"
                width="120">
            <template slot-scope="scope">
                <el-button
                        @click.native.prevent="selectRow(scope.$index, userTrainings)"
                        type="success">
                    Select
                </el-button>
            </template>
        </el-table-column>
        <el-table-column
                label="Operation"
                width="105">
            <template slot-scope="scope">
                <el-button
                        @click.native.prevent="editTrain(scope.$index, userTrainings)"
                        type="primary"
                        width="100">
                    Edit
                </el-button>
            </template>
        </el-table-column>
        <el-table-column
                label="Operation"
                width="120">
            <template slot-scope="scope">
                <el-button
                        @click.native.prevent="deleteRow(scope.$index, userTrainings)"
                        type="danger">
                    Remove
                </el-button>
            </template>
        </el-table-column>
    </el-table>
      <div>
        <small class="tet-muted">DataCurrent: {{this.userTrainings}}</small>
      </div>
    </div>
</template>

<script>
    export default {
        name: "TrainingsTable",
        methods: {
            deleteRow(index, rows) {
                this.delTrain = (rows[index])
                this.$http.delete('api/training/delTrainingById/'+this.delTrain.id)
                    .then(response => {
                        console.log(response.status);
                        rows.splice(index, 1);
                        alert('Training deleted')
                    })
                    .catch(response => {
                        //debugger
                        alert(response.status)
                    })
            },
            selectRow(index, rows) {
                this.training = (rows[index])

                let str = this.training.name
                this.$router.push({name: 'train_display', params: {trName: str}})
            },
            editTrain(index, rows){
                this.editTraining = (rows[index])
                let str = this.editTraining.name
                this.$router.push({name: 'EditTraining', params: {trName: str}})
            }
        },
        data() {
            return {
                userTrainings: [],
                training: null,
                delTrain: null,
                trName: null,
                editTraining: null
            }
        },
        mounted() {
            this.$http.get('api/training/email/igorbasket93@gmail.com/')
                .then(response => {
                    this.userTrainings = response.data
                  this.userTrainings.filter((value, index) => {

                    let minutes = Math.floor(Number((value.trainingDuration) / 60) % 60).toString()
                    Number(minutes) < 10 ? (minutes = "0" + minutes.toString()):minutes

                    let seconds = Number(value.trainingDuration % 60)
                    Number(seconds) < 10 ? (seconds = "0" + seconds.toString()):seconds

                    this.userTrainings[index].trainingDuration ="00:" + minutes
                        + ":" + seconds
                  });

                })
                .catch(response => {
                    //debugger
                    alert(response.status)
                })
        },
    }
</script>

<style scoped>



</style>