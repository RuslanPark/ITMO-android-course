package com.example.photoviewerapp

class Url (val url : String)
class Item (val sizes : List<Url>, val text : String)
class Items (val count : Int, val items : List<Item>)
class Response (val response : Items)
