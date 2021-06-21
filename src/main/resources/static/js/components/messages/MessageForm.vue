<template>
    <v-layout row>
        <v-text-field label="New message" placeholder="Your message" v-model="text"/>
        <v-btn  @click="save" text>
            <span class="material-icons">note_add</span>
        </v-btn>
    </v-layout>
</template>

<script>

    import { mapActions } from "vuex";

    export default {
        props: ['messageAttr'],
        data() {
            return {
                text: '',
                id: ''
            }
        },
        watch: {
            messageAttr: function(newVal, oldVal) {
                this.text = newVal.text;
                this.id = newVal.id;
            }
        },
        methods: {
            ...mapActions(['addMessageAction', 'updateMessageAction']),
            save() {
                const message = { id: this.id, text: this.text }
                if (this.id) {
                    this.updateMessageAction(message)
                } else {
                    this.addMessageAction(message)
                }

                this.id = '';
                this.text = '';
            }
        }
    }
</script>

<style>

</style>