<template>
    <div class="container" id="vue-app">
        <div align="center">
        <input  style="width: 400px"
                type="text"
                v-model="nameTrain"
                class="form-control"
                placeholder="Input training name">
            </div>
        <div class="row">
            <div class="col-sm-12">
                <h3>My exercises {{todoCount}}</h3>

                <ul class="list-group" v-if="listEx.length > 0">
                    <li class="list-group-item"
                        v-for="todo in listEx"
                        :key="todo.id">
                        {{todo.title}} ({{todo.description}}) - {{todo.time + ' seconds'}}
                        <button class="btn btn-warning btn-xs pull-right" v-on:click="deleteTodo(todo)">Delete</button>
                    </li>
                </ul>
                <div v-else><h1>You don't have any exercises</h1></div>
            </div>
        </div>
        <i style="font-size: large">Creating an exercise:</i>
        <div align="center">
        <div class="row">
            <div class="col-sm-12">
                <form v-on:submit.prevent="addNewEx(newEx)">
                    <div class="form-group" style="width: 400px">
                        <input
                                type="text"
                                v-model="newEx.title"
                                class="form-control"
                                placeholder="Input exercise name">
                        <input
                                type="text"
                                v-model="newEx.description"
                                class="form-control"
                                placeholder="Input exercise description">
                        <input
                                type="Number"
                                v-model="newEx.time"
                                class="form-control"
                                placeholder="Input exercise duration in seconds">
                    </div>

                    <div class="form-group">
                        <button class="btn btn-primary"
                        >Add exercise</button>
                    </div>
                    <div>
                    <button class="btn btn-success" @click="post()">Creat training</button>
                    </div>
                </form>
                <small class="tet-muted">Data: {{listExerciseDto}}</small>
            </div>
        </div>
    </div>
    </div>
</template>

<script>
    export default {
        name: "CreateNewTraining",
        el: '#vue-app',
        data() {
            return {
                listEx: [{id: 1, title: 'Jumping', description: 'raise your knees to your waist', time: 80}],
                newEx: {id: null, title: '', description: '', time: null},
                listExerciseDto: [{name: 'Jumping', description: 'raise your knees to your waist', time: 80}],
                nameTrain: ''
            }
            },
        computed: {
            todoCount() {
                return this.listEx.length
            }
        },
        methods: {
            addNewEx(newEx) {
                this.listEx.push(newEx)
                let add = {name: this.newEx.title, description: '', time: this.newEx.time}
                this.newEx = {id: null, title: '', time: null}

                this.listExerciseDto.push(add)

            },

            deleteTodo(todo) {
                let index = this.listEx.indexOf(todo);
                if (index !== -1) {
                    this.listEx.splice(index, 1);
                    this.listExerciseDto.splice(index, 1);
                }
            },
            post: function () {
                this.$http.post('/api/training/newTraining',{
                    name: this.nameTrain,
                    email: 'igorbasket@gmail.com',
                    listExerciseDto: this.listExerciseDto
                }).then(response => {
                    let a = response.data;
                    alert(a);
                    this.$router.push('/user_training_list')
                })
            },
        }
    }
</script>

<style scoped>
    .margin-right-10 {
        margin-right: 10px;
    }

</style>