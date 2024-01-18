<%@page import="java.security.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="myClass.Post" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mohil Mokaria</title>
        <!-- BOOTSTRAP CDNs -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <!-- LOCAL CSS LINK -->
        <link rel="stylesheet" href="./post_style.css" />
    </head>
    <body>
        <div class="container">
            <div class="row align-items-center">
                <div class="col-md-10 col-sm-8 mx-auto">
                    <div class="card shadow align-items-center">
                        <div class="card-body d-flex flex-column col-md-9 col-sm-6 mb-4 mt-4">
                            <!-- Form for posting new content -->
                            <form action="SocialServlet" method="post">
                                <div class="mb-4">
                                    <h2>New Post</h2>
                                </div>
                                <!-- Input field for post title -->
                                <div class="form-outline mb-4">
                                    <label  for="title"  class="form-label">Title</label>
                                    <input type="text" id="title" name="title" class="form-control" required/>
                                </div>
                                <!-- Textarea for post body -->
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="body">Body</label>
                                    <textarea id="body" name="body" class="form-control" required></textarea>
                                </div>
                                <!-- Button to submit the post -->
                                <center><button type="submit" class="btn btn-outline-primary btn-block mb-2 sendbtn">Post</button></center>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Horizontal line for visual separation -->
        <hr class="border border-primary border-2">
        
        <!-- Section displaying existing posts -->
        <section>
            <div class="row align-items-center mt-4 mb-4">
                <div class="col-md-9 col-sm-8 mx-auto">
                    <div class="card-body d-flex flex-column">
                        <!-- Iterate through the list of posts -->
                        <%
                            List<Post> posts = (List<Post>) request.getAttribute("postList");

                            if (posts != null && !posts.isEmpty()) {
                                for (Post post : posts) {
                        %>
                        <!-- Card for each post -->
                        <div class="card mb-2">
                            <div class="card-header">
                                <%= post.getTitle()%>
                            </div>
                            <div class="card-body">
                                <!-- Blockquote containing post body and timestamp -->
                                <blockquote class="blockquote mb-0">
                                    <p><%= post.getBody()%></p>
                                    <footer class="blockquote-footer text-muted"><small><%= post.getFormattedMsgTime()%></small></footer>
                                </blockquote>
                            </div>
                        </div>
                        <%
                            }
                        } else {
                        %>
                        <!-- Card for no posts available -->
                        <div class="card messages_card">
                            <div class="card-body">
                                <div class="row">
                                    <h5 class="card-title col">No Posts Yet</h5>
                                </div>
                                <p class="card-text">Try Posting Some Stuff!</p>
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
