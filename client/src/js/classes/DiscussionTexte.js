import Discussion from './Discussion.js';
import Message from './Message.js';

class DiscussionTexte extends Discussion {
  constructor(id, utilisateurs) {
    super(id, utilisateurs);
    this.messageList = [];
  }
}

export default DiscussionTexte;
