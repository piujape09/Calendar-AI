import 'dart:convert';
import 'package:http/http.dart' as http;
import '../config/app_config.dart';
import '../models/event.dart';

class ApiService {
  static const String defaultBaseUrl = 'http://10.0.2.2:8080/api';
  final String baseUrl;
  ApiService({String? baseUrl}) : baseUrl = baseUrl ?? AppConfig.backendBaseUrl;

  Future<Map<String, dynamic>> uploadFile(String filename, List<int> bytes) async {
    final uri = Uri.parse('$baseUrl/upload');
    final request = http.MultipartRequest('POST', uri);
    request.files.add(http.MultipartFile.fromBytes('file', bytes, filename: filename));
    final streamed = await request.send();
    final res = await http.Response.fromStream(streamed);
    if (res.statusCode >= 400) {
      throw Exception('Upload failed: ${res.body}');
    }
    return json.decode(res.body) as Map<String, dynamic>;
  }

  Future<List<EventModel>> extractEvents(int documentId) async {
    final uri = Uri.parse('$baseUrl/extract?documentId=$documentId');
    final res = await http.post(uri);
    if (res.statusCode >= 400) {
      throw Exception('Extraction failed: ${res.body}');
    }
    final list = json.decode(res.body) as List<dynamic>;
    return list.map((e) => EventModel.fromJson(e as Map<String, dynamic>)).toList();
  }

  Future<List<EventModel>> fetchEvents() async {
    final uri = Uri.parse('$baseUrl/calendar/list');
    final res = await http.get(uri);
    if (res.statusCode >= 400) {
      throw Exception('History fetch failed: ${res.body}');
    }
    final list = json.decode(res.body) as List<dynamic>;
    return list.map((e) => EventModel.fromJson(e as Map<String, dynamic>)).toList();
  }

  Future<List<Map<String, dynamic>>> fetchHistory() async {
    final uri = Uri.parse('$baseUrl/history');
    final res = await http.get(uri);
    if (res.statusCode >= 400) {
      throw Exception('History fetch failed: ${res.body}');
    }
    return (json.decode(res.body) as List<dynamic>).cast<Map<String, dynamic>>();
  }
}
