<template>
    <v-container>
        <v-layout align-content-space-around justify-start column>
            <message-form :messageAttr="message" />
            <message-row
                    v-for="message in sortedMessages"
                    :key="message.id"
                    :message="message"
                    :editMessage="editMessage" />
        </v-layout>
    </v-container>
</template>

<script>
    import MessageForm from "components/messages/MessageForm.vue";
    import MessageRow from "components/messages/MessageRow.vue";
    import {mapGetters} from "vuex";

    export default {
        components: {
            MessageForm,
            MessageRow
        },
        data() {
            return {
                message: null
            }
        },
        computed:
            mapGetters(['sortedMessages']),
        methods: {
            editMessage(message) {
                this.message = message;
            }
        }
    }
</script>

<style>

</style>