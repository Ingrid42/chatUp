import Discussion from './Discussion.js';
import Message from './Message.js';

class DiscussionTexte extends Discussion {
  constructor() {
    super();
    this.messageList = [];
  }

  addMessage(message) {
    this.messageList.push(message);
  }
}

export default DiscussionTexte;
