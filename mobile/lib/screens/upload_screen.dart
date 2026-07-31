import 'dart:io';
import 'package:flutter/material.dart';
import 'package:file_picker/file_picker.dart';
import '../models/event.dart';
import '../services/api_service.dart';
import 'event_preview_screen.dart';

class UploadScreen extends StatefulWidget {
  final bool isPdf;
  const UploadScreen({super.key, required this.isPdf});

  @override
  State<UploadScreen> createState() => _UploadScreenState();
}

class _UploadScreenState extends State<UploadScreen> {
  bool loading = false;
  List<EventModel> events = [];
  String? errorMessage;

  Future<void> pickAndUpload() async {
    final result = await FilePicker.platform.pickFiles(
      type: widget.isPdf ? FileType.custom : FileType.image,
      allowedExtensions: widget.isPdf ? ['pdf'] : ['png', 'jpg', 'jpeg'],
      withData: true,
    );
    if (result == null || result.files.isEmpty) return;

    setState(() {
      loading = true;
      errorMessage = null;
    });

    try {
      final file = result.files.first;
      final fileBytes = file.bytes ?? await File(file.path!).readAsBytes();
      final response = await ApiService().uploadFile(file.name, fileBytes);
      final documentId = response['documentId'] as int;
      final extracted = await ApiService().extractEvents(documentId);
      setState(() {
        events = extracted;
      });
      if (extracted.isNotEmpty) {
        if (!mounted) return;
        Navigator.push(context, MaterialPageRoute(builder: (_) => EventPreviewScreen(events: extracted)));
      }
    } catch (e) {
      setState(() {
        errorMessage = e.toString();
      });
    } finally {
      if (mounted) {
        setState(() => loading = false);
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(widget.isPdf ? 'Upload PDF' : 'Upload Image')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            ElevatedButton.icon(
              onPressed: loading ? null : pickAndUpload,
              icon: const Icon(Icons.upload_file),
              label: const Text('Pick and Upload'),
            ),
            const SizedBox(height: 16),
            if (loading) const CircularProgressIndicator(),
            if (errorMessage != null)
              Padding(
                padding: const EdgeInsets.only(top: 12),
                child: Text(errorMessage!, style: const TextStyle(color: Colors.red)),
              ),
            const SizedBox(height: 16),
            Expanded(
              child: ListView.builder(
                itemCount: events.length,
                itemBuilder: (_, i) {
                  final e = events[i];
                  return Card(
                    child: ListTile(
                      title: Text(e.title),
                      subtitle: Text('${e.date} ${e.time}\n${e.location ?? ''}'),
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}
