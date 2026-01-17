// Module 3: Stack and Queue - Messaging and Notification System
// Stack: LIFO (Last In First Out) - for notification history
// Queue: FIFO (First In First Out) - for incoming messages

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Message class
class Message {
    private String sender;
    private String receiver;
    private String content;
    private LocalDateTime timestamp;
    private boolean isRead;

    public Message(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
    }

    public void markAsRead() {
        this.isRead = true;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String status = isRead ? "[READ]" : "[UNREAD]";
        return status + " From: " + sender + " | \"" + content + "\" (" +
                timestamp.format(formatter) + ")";
    }

    public String getSender() { return sender; }
}

// Notification class
class Notification {
    private String message;
    private LocalDateTime timestamp;
    private String type; // "friend_request", "like", "comment", etc.

    public Notification(String message, String type) {
        this.message = message;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "[" + type.toUpperCase() + "] " + message + " (" +
                timestamp.format(formatter) + ")";
    }
}

// Custom Queue implementation for Messages (using array)
class MessageQueue {
    private Message[] queue;
    private int front;  // Points to first element
    private int rear;   // Points to last element
    private int size;
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new Message[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    // Enqueue - add message to the end of queue (FIFO)
    public void enqueue(Message message) {
        if (isFull()) {
            System.out.println("✗ Message queue is full! Cannot receive more messages.");
            return;
        }
        rear = (rear + 1) % capacity;  // Circular queue
        queue[rear] = message;
        size++;
        System.out.println("✓ New message received from " + message.getSender());
    }

    // Dequeue - remove and return first message (FIFO)
    public Message dequeue() {
        if (isEmpty()) {
            System.out.println("✗ No messages in queue!");
            return null;
        }
        Message message = queue[front];
        queue[front] = null;
        front = (front + 1) % capacity;  // Circular queue
        size--;
        return message;
    }

    // Peek - view first message without removing
    public Message peek() {
        if (isEmpty()) {
            System.out.println("✗ No messages in queue!");
            return null;
        }
        return queue[front];
    }

    // Display all messages in queue
    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("No pending messages.");
            return;
        }

        System.out.println("\n=== Message Queue (" + size + " messages) ===");
        int count = 0;
        int index = front;
        while (count < size) {
            System.out.println((count + 1) + ". " + queue[index]);
            index = (index + 1) % capacity;
            count++;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public int getSize() {
        return size;
    }
}

// Custom Stack implementation for Notifications (using array)
class NotificationStack {
    private Notification[] stack;
    private int top;  // Points to the top element
    private int capacity;

    public NotificationStack(int capacity) {
        this.capacity = capacity;
        this.stack = new Notification[capacity];
        this.top = -1;  // Empty stack
    }

    // Push - add notification to top of stack (LIFO)
    public void push(Notification notification) {
        if (isFull()) {
            System.out.println("✗ Notification stack is full!");
            return;
        }
        top++;
        stack[top] = notification;
        System.out.println("✓ New notification: " + notification.toString());
    }

    // Pop - remove and return top notification (LIFO)
    public Notification pop() {
        if (isEmpty()) {
            System.out.println("✗ No notifications!");
            return null;
        }
        Notification notification = stack[top];
        stack[top] = null;
        top--;
        return notification;
    }

    // Peek - view top notification without removing
    public Notification peek() {
        if (isEmpty()) {
            System.out.println("✗ No notifications!");
            return null;
        }
        return stack[top];
    }

    // Display all notifications (from top to bottom)
    public void displayStack() {
        if (isEmpty()) {
            System.out.println("No notifications.");
            return;
        }

        System.out.println("\n=== Notification History (Most Recent First) ===");
        for (int i = top; i >= 0; i--) {
            System.out.println((top - i + 1) + ". " + stack[i]);
        }
    }

    // Display notifications from bottom to top (chronological order)
    public void displayChronological() {
        if (isEmpty()) {
            System.out.println("No notifications.");
            return;
        }

        System.out.println("\n=== Notification History (Chronological) ===");
        for (int i = 0; i <= top; i++) {
            System.out.println((i + 1) + ". " + stack[i]);
        }
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == capacity - 1;
    }

    public int getSize() {
        return top + 1;
    }
}

// Student Messaging System
class MessagingStudent {
    private String name;
    private MessageQueue incomingMessages;
    private NotificationStack notifications;

    public MessagingStudent(String name) {
        this.name = name;
        this.incomingMessages = new MessageQueue(10);  // Max 10 pending messages
        this.notifications = new NotificationStack(20); // Max 20 notifications
    }

    // Send message to another student
    public void sendMessage(MessagingStudent recipient, String content) {
        Message message = new Message(this.name, recipient.name, content);
        recipient.receiveMessage(message);

        // Create notification for sender
        Notification sentNotif = new Notification(
                "You sent a message to " + recipient.name, "sent");
        this.notifications.push(sentNotif);
    }

    // Receive message (enqueue)
    public void receiveMessage(Message message) {
        incomingMessages.enqueue(message);

        // Create notification for new message
        Notification receivedNotif = new Notification(
                "New message from " + message.getSender(), "message");
        notifications.push(receivedNotif);
    }

    // Read next message (dequeue - FIFO)
    public void readNextMessage() {
        System.out.println("\n--- Reading Next Message ---");
        Message message = incomingMessages.dequeue();
        if (message != null) {
            message.markAsRead();
            System.out.println(message);

            // Create notification for read message
            Notification readNotif = new Notification(
                    "You read a message from " + message.getSender(), "read");
            notifications.push(readNotif);
        }
    }

    // View latest notification (peek - LIFO)
    public void viewLatestNotification() {
        System.out.println("\n--- Latest Notification ---");
        Notification notif = notifications.peek();
        if (notif != null) {
            System.out.println(notif);
        }
    }

    // Dismiss latest notification (pop - LIFO)
    public void dismissLatestNotification() {
        System.out.println("\n--- Dismissing Notification ---");
        Notification notif = notifications.pop();
        if (notif != null) {
            System.out.println("Dismissed: " + notif);
        }
    }

    public void displayMessages() {
        incomingMessages.displayQueue();
    }

    public void displayNotifications() {
        notifications.displayStack();
    }

    public void displayNotificationsChronological() {
        notifications.displayChronological();
    }

    public String getName() { return name; }
}

public class CampusSocialNetworkModule3 {
    public static void main(String[] args) {
        // Create students
        MessagingStudent alice = new MessagingStudent("Alice");
        MessagingStudent bob = new MessagingStudent("Bob");
        MessagingStudent charlie = new MessagingStudent("Charlie");

        System.out.println("=== QUEUE DEMO: Incoming Messages (FIFO) ===\n");

        // Send messages to Alice (they will be queued)
        bob.sendMessage(alice, "Hey Alice, want to study together?");
        charlie.sendMessage(alice, "Don't forget about the project meeting!");
        bob.sendMessage(alice, "The library is open until 10 PM.");

        // Display Alice's message queue
        alice.displayMessages();

        // Alice reads messages in FIFO order (first message received is read first)
        alice.readNextMessage();
        alice.readNextMessage();

        // Check remaining messages
        alice.displayMessages();

        System.out.println("\n=== STACK DEMO: Notifications (LIFO) ===\n");

        // Display Alice's notifications (most recent first - LIFO)
        alice.displayNotifications();

        // View latest notification without removing
        alice.viewLatestNotification();

        // Dismiss notifications (LIFO - last in, first out)
        alice.dismissLatestNotification();
        alice.dismissLatestNotification();

        // Display remaining notifications
        alice.displayNotifications();

        // Display in chronological order (oldest to newest)
        alice.displayNotificationsChronological();

        System.out.println("\n=== PRACTICAL USE CASE ===\n");
        System.out.println("Queue (FIFO) is used for:");
        System.out.println("- Processing messages in order received");
        System.out.println("- Handling requests fairly (first come, first served)");
        System.out.println("- Print job queues, customer service queues");

        System.out.println("\nStack (LIFO) is used for:");
        System.out.println("- Undo/Redo operations");
        System.out.println("- Browser back button (most recent page first)");
        System.out.println("- Function call stack in programming");
        System.out.println("- Notification history (latest first)");
    }
}