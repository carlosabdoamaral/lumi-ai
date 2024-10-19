import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class ChatView extends StatefulWidget {
  const ChatView({super.key});

  @override
  State<ChatView> createState() => _ChatViewState();
}

class _ChatViewState extends State<ChatView> {
  @override
  Widget build(BuildContext context) {
    double deviceHeight = MediaQuery.of(context).size.height;
    bool inputIsFocused = false;
    double chatHeight = deviceHeight * 0.75;

    return Scaffold(
      appBar: AppBar(
        title: Text(
          "Lumi",
        ),
        centerTitle: false,
      ),
      body: Stack(
        children: [
          SingleChildScrollView(
            physics: const AlwaysScrollableScrollPhysics(),
            child: Container(
              constraints: BoxConstraints(
                maxHeight: chatHeight
              ),
              child: Column(
                children: [
                  for (var i in [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
                    Container(
                      margin: EdgeInsets.symmetric(vertical: 5),
                      height: 50,
                      width: double.infinity,
                      color: Colors.grey.withOpacity(0.4),
                    ),
                ],
              ),
            ),
          ),
          const Spacer(),
          Container(
            margin: EdgeInsets.only(left: 20, right: 20, top: chatHeight + 20),
            padding: const EdgeInsets.only(
              left: 15,
              right: 5,
              top: 5,
              bottom: 5,
            ),
            decoration: BoxDecoration(
              color: Colors.grey[100],
              border: Border.all(color: Colors.grey),
              borderRadius: BorderRadius.all(
                Radius.circular(200),
              ),
            ),
            child: TextField(
              decoration: InputDecoration(
                // suffix: CircleAvatar(
                //   backgroundColor: Colors.black,
                //   child: Icon(
                //     Icons.arrow_upward_outlined,
                //     color: Colors.white,
                //   ),
                // ),
                suffixIcon: Container(
                  decoration: BoxDecoration(
                      color: Colors.black,
                      borderRadius: BorderRadius.circular(100)),
                  child: Icon(
                    Icons.arrow_upward_outlined,
                    color: Colors.white,
                  ),
                ),
                hintText: "Insira sua mensagem...",
                fillColor: Colors.purple,
                focusColor: Colors.green,
                border: InputBorder.none,
              ),
            ),
          ),
        ],
      ),
    );
  }
}
