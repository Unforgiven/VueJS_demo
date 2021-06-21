<template>
    <v-app>
        <v-app-bar app>
            <v-app-bar-title>Sarafan</v-app-bar-title>
            <v-btn text
                   v-if="profile"
                   :disabled="$route.path === '/'"
                   @click="showMessages">
                Записи
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn text
                   v-if="profile"
                   :disabled="$route.path === '/profile'"
                   @click="showProfile">
                {{profile.name}}&nbsp;
            </v-btn>
            <v-btn v-if="profile" icon href="/logout">
                <span class="material-icons">logout</span>
            </v-btn>
        </v-app-bar>
        <v-main>
            <router-view></router-view>
        </v-main>
    </v-app>
</template>

<script>
    import {addHandler} from "util/ws";
    import {mapState, mapMutations} from 'vuex';

    export default {
        computed: mapState(['profile']),
        methods: {
            ...mapMutations(['addMessageMutation', 'updateMessageMutation', 'removeMessageMutation']),
            showMessages() {
                this.$router.push('/');
            },
            showProfile() {
                this.$router.push('/profile');
            }
        },
        created() {
            addHandler(data => {
                if (data.objectType === 'MESSAGE') {

                    switch (data.eventType) {
                        case 'CREATE':
                            this.addMessageMutation(data.body)
                            break
                        case 'UPDATE':
                            this.updateMessageMutation(data.body)
                            break
                        case 'REMOVE':
                            this.removeMessageMutation(data.body)
                            break
                        default:
                            console.error(`Looks like event type is unknown: "${data.eventType}"`)
                    }
                } else {
                    console.error(`Looks like object type is unknown: "${data.objectType}"`)
                }

            })
        },
        beforeMount() {
            if(!this.profile) {
                this.$router.replace('/auth');
            }
        }
    }
</script>

<style>
    .main-app{
        /*color: maroon;*/
    }
</style>