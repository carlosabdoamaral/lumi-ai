<script lang="ts">
import axios from 'axios';
import showdown from 'showdown';

export default {
  data() {
    return {
      chats: [
        {
          uuid: "abc123",
          name: "Como aplicar DataScience na área da saúde",
          createdAt: "21/09/2024",
          messages: [
            {
              content: "Olá, me chamo Lumi!",
              role: "assistant"
            },
            {
              content: "Como você está se sentindo hoje?",
              role: "assistant"
            },
            {
              content: "Estou aqui para ajudar no que precisar.",
              role: "assistant"
            },
            {
              content: "Eu me sinto um pouco cansado.",
              role: "user"
            },
            {
              content: "Entendo, tem algo específico que está te deixando assim?",
              role: "assistant"
            },
            {
              content: "Acho que é só o trabalho, muitas responsabilidades.",
              role: "user"
            },
            {
              content: "Isso pode ser difícil. Já tentou tirar um tempo para você relaxar?",
              role: "assistant"
            },
            {
              content: "Claro! Aqui está um texto lorem ipsum tradicional: Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ac interdum quam. Nulla facilisi. Proin at massa nec odio commodo sollicitudin. Sed sit amet ipsum sed urna commodo fermentum. Integer in lorem eget leo consectetur laoreet. Vivamus ac blandit ipsum. Ut consectetur ligula sed libero convallis, quis aliquam ipsum tincidunt. Duis ac justo nec nisl dictum finibus id ut lectus. Quisque scelerisque orci a scelerisque convallis. In hac habitasse platea dictumst. Phasellus volutpat vitae ante ac lacinia. Donec blandit nunc at justo fringilla sollicitudin. Maecenas fringilla bibendum sem, vel varius nunc congue non. Espero que este texto seja útil para você. Se precisar de mais alguma coisa, estou aqui para ajudar!",
              role: "assistant"
            }
          ]
        },
        {
          uuid: "abc124",
          name: "Exemplo 2",
          createdAt: "22/09/2024",
          messages: [
            {
              content: "Olá, me chamo Lumi!",
              role: "assistant"
            },
            {
              content: "Como você está se sentindo hoje?",
              role: "assistant"
            },
            {
              content: "Hoje meu dia foi incrível!!",
              role: "user"
            },
            {
              content: "Fico feliz em saber disso! O que fez seu dia ser especial?",
              role: "assistant"
            },
            {
              content: "Eu passei o dia com meus amigos.",
              role: "user"
            },
            {
              content: "Que ótimo! Passar tempo com quem gostamos realmente faz bem.",
              role: "assistant"
            },
            {
              content: "Sim, e nós fomos a um parque. Foi muito relaxante.",
              role: "user"
            },
            {
              content: "Parece ter sido um dia maravilhoso! Alguma parte foi sua favorita?",
              role: "assistant"
            }
          ]
        },
        {
          uuid: "abc125",
          name: "Exemplo 3",
          createdAt: "23/09/2024",
          messages: [
            {
              content: "Olá, como você está?",
              role: "assistant"
            },
            {
              content: "Não estou muito bem, para ser honesto.",
              role: "user"
            },
            {
              content: "Sinto muito em ouvir isso. Quer compartilhar o que está acontecendo?",
              role: "assistant"
            },
            {
              content: "Só me sinto meio perdido ultimamente.",
              role: "user"
            },
            {
              content: "Isso pode ser muito difícil. Estou aqui para te escutar.",
              role: "assistant"
            },
            {
              content: "Obrigado, acho que falar sobre isso já ajuda um pouco.",
              role: "user"
            }
          ]
        }
      ],
      currentChatIdx: 0,
      chatInputContent: "",
      searchbarInputContent: "",
    }
  },
  watch: {
    chats() {
      this.doScroll();
    }
  },
  methods: {
    getCurrentChatDetails() {
      return this.$data.chats[this.$data.currentChatIdx]
    },
    async getAnswer() {
      try {
        const res = await axios.post('http://localhost:8080/ai', {
          messages: this.getCurrentChatDetails().messages
        })
        this.getCurrentChatDetails().messages = res.data.messages
      } catch (err) {
        this.$buefy.notification.open('Ocorreu um erro inesperado.')
      }
    },
    markdownToHtml(content: any) {
      const converter = new showdown.Converter();
      return converter.makeHtml(content)
    },
    addBotMessage(text: string) {
      this.$data.chats[this.$data.currentChatIdx].messages.push({
        content: text.trim(),
        role: "assistant"
      })

      this.doScroll();
    },
    doScroll() {
      const chatWrapper = window.document.querySelector('#chatWrapper');
      chatWrapper?.scrollTo({
        top: window.outerHeight * chatWrapper?.scrollHeight,
        behavior: 'smooth'
      });

    },
    addUserMessage() {
      let canPush = !!this.$data.chatInputContent.trim()
      if (!canPush) this.$buefy.notification.open('Por favor, digite algo para enviar')

      this.$data.chats[this.$data.currentChatIdx].messages.push({
        content: this.$data.chatInputContent.trim(),
        role: "user"
      })
      this.$data.chatInputContent = '';

      this.doScroll();
      // this.getAnswer()
      // this.addBotMessage("")
    },
    searchIconClick() {
      alert('You wanna make a search?')
    },
  }
}
</script>

<template>
  <div class="chat">
    <div class="chat-list">
      <b-field>
        <b-input placeholder="Buscar" type="text" class="custom-input bg-white" />
      </b-field>

      <div v-for="(chat, index) in chats"
        class="chat-list-item {{ index === this.$data.currentChatIdx ? 'chat-list-item-active': '' }}" :key="index">
        <div class="chat-list-item-content">
          <p>{{ chat.name }}</p>
        </div>
      </div>
    </div>

    <div class="chat-content">
      <div class="chat-body" id="chatWrapper">
        <div v-for="(msg, index) in getCurrentChatDetails().messages" :key="index">
          <div class="msg bot-msg" v-if="msg.role === 'assistant' && msg.content == ''">
            <div class="bot-msg-avatar"></div>
            <p class="msg-pending"></p>
          </div>
          <div class="msg bot-msg" v-else-if="msg.role === 'assistant'">
            <div class="bot-msg-avatar"></div>
            <p v-html="markdownToHtml(msg.content)"></p>
          </div>

          <div class="msg user-msg" v-if="msg.role === 'user'">
            <p v-html="markdownToHtml(msg.content)"></p>
          </div>
        </div>
      </div>
      <div class="chat-footer">
        <b-input class="custom-input" placeholder="Insira seu texto" v-model="chatInputContent" type="text"
          icon-right="send" @keyup.enter="addUserMessage()" icon-right-clickable @icon-right-click="addUserMessage()">
        </b-input>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
// DARK
$background: rgb(255, 255, 255);
$active: rgb(238, 242, 248);
$secondary: rgb(131, 134, 151);
$black: rgb(19, 19, 19);
$sideIcons: rgb(171, 174, 185);

$accent: rgb(0, 0, 146);
$userMessageBackground: rgb(0, 0, 146);
$botMessageBackground: rgb(245, 245, 255);
$chatsItemBackground: rgb(234, 234, 234);

$sidebarBackground: rgb(248, 248, 248);
$sidebarWidth: 300px;

$inputHeight: 120px;
$searchbarBackground: rgb(248, 248, 248);

* {
  font-family: 'Poppins' !important;

  p {
    font-weight: 500;
    color: #fff;
  }

  small {
    color: $secondary;
    font-weight: 400;
  }
}


.chat {
  background: $background;
  height: 100vh !important;
  max-height: 100vh !important;
  width: 100vw !important;
  max-width: 100vw !important;
  display: flex;
  flex-direction: row;
}

.custom-input {
  * {
    border: none;
    box-shadow: inset 0 0 0px #f8a100;

    margin: auto 0;
    width: 100%;
    background: $active;
    border-radius: 13px;
    color: $secondary;
    border: none;
    height: 60px;

    display: flex;
    flex-direction: row;
    align-items: center;

    &::placeholder {
      color: $secondary;
    }
  }
}

.chat-list {
  padding: 1em;
  width: max-content;
  height: 100dvh;
  width: 300px;
  display: flex;
  flex-direction: column;
  background-color: $sidebarBackground;

  .chat-list-item {
    background: $chatsItemBackground;
    border-radius: 13px;
    color: $black;
    border: none;
    padding: 15px;
    gap: 15px;
    margin-bottom: .5em;
    cursor: pointer;

    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;

    .chat-list-item-avatar {
      width: 50px;
      background: $accent;
      aspect-ratio: 1/1;
      border-radius: 10px;
    }

    .chat-list-item-content {
      p {
        color: $secondary;
        font-weight: 400;
        font-size: 0.9rem;
      }

      display: flex;
      flex-direction: column;
    }
  }
}

.chat-content {
  width: calc(100vw - $sidebarWidth) !important;
  color: #FFF;
  padding: 1em;
  height: 100%;

  .chat-body {
    // display: flex;
    // width: 100%;
    // flex-direction: column;
    // justify-content: end;
    // flex-grow: 1;
    // height: calc(100% - $inputHeight);

    .msg {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: start;

      height: max-content;

      width: calc((100vw - 300px - 2em) / 2);
      margin-bottom: 1em;
      margin-left: none;

      p {
        border-radius: 13px;
        min-height: 50px;
        padding: 1em;
        font-weight: 300 !important;
      }
    }

    .msg-pending {
      overflow: hidden;
      display: inline-block;
      vertical-align: bottom;
    }

    .msg-pending::after {
      content: "";
      display: inline-block;
      animation: ellipsis 2s infinite;
    }

    .bot-msg {
      display: flex;
      flex-direction: row;
      align-items: start;
      justify-content: start;
      gap: 15px;

      .bot-msg-avatar {
        width: 50px !important;
        height: 50px !important;
        border-radius: 100em;
        background: $accent;
        background-image: url('../assets/icons/lumi_logo.svg');
        background-repeat: no-repeat;
        background-position: center;
      }

      p {
        max-width: calc(100% - 100px);
        background: $botMessageBackground;
        color: $secondary;
      }
    }

    .user-msg {
      margin-left: auto;

      p {
        background: $userMessageBackground;
        margin-left: auto;
      }
    }
  }

  .chat-footer {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: $inputHeight;

    .custom-input {
      width: 100%;
    }
  }
}

@keyframes ellipsis {
  0% {
    content: "";
  }

  33% {
    content: ".";
  }

  66% {
    content: "..";
  }

  100% {
    content: "...";
  }
}

.bg-white {
  * {
    background: $searchbarBackground;
  }
}

.chat-body {
  height: calc(100% - $inputHeight);
  overflow-y: auto;
  display: block;
  flex-direction: column;
  align-items: flex-start;
  justify-content: flex-end;
}
</style>
