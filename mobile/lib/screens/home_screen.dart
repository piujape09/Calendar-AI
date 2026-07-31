import 'package:flutter/material.dart';
import 'upload_screen.dart';
import 'history_screen.dart';

class HomeScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('CalendarAI')),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Single-user calendar assistant', style: Theme.of(context).textTheme.titleLarge),
            const SizedBox(height: 8),
            Text('Upload a PDF or image, extract likely events, and review them in one simple flow.', style: Theme.of(context).textTheme.bodyMedium),
            const SizedBox(height: 24),
            FilledButton.icon(
              icon: const Icon(Icons.picture_as_pdf),
              label: const Text('Upload PDF'),
              onPressed: () => Navigator.push(context, MaterialPageRoute(builder: (_) => const UploadScreen(isPdf: true))),
            ),
            const SizedBox(height: 12),
            FilledButton.icon(
              icon: const Icon(Icons.image),
              label: const Text('Upload Image'),
              onPressed: () => Navigator.push(context, MaterialPageRoute(builder: (_) => const UploadScreen(isPdf: false))),
            ),
            const SizedBox(height: 12),
            OutlinedButton.icon(
              icon: const Icon(Icons.history),
              label: const Text('View History'),
              onPressed: () => Navigator.push(context, MaterialPageRoute(builder: (_) => const HistoryScreen())),
            ),
          ],
        ),
      ),
    );
  }
}
