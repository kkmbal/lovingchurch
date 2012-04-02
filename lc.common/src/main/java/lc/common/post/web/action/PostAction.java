package lc.common.post.web.action;

import java.util.List;

import javax.annotation.Resource;

import lc.common.post.domain.Post;
import lc.common.post.service.PostService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Scope("prototype")
@Service("postAction")
public class PostAction   extends ActionSupport implements ModelDriven<Post>{
	protected Log logger = LogFactory.getLog(getClass());
	
	@Resource(name="postService")
	private PostService postService;
	private Post post;
	private List<Post> listPost;
	
	public PostAction(){
		post = new Post();
	}
	
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public List<Post> getListPost() {
		return listPost;
	}

	public void setListPost(List<Post> listPost) {
		this.listPost = listPost;
	}

	public String listZip() throws Exception{
		if(!"".equals(post.getKEYWORD())){
			listPost = postService.listZip(post);
			post.setKEYWORD(null);
		}
		return "success";
	}
	
	public Post getModel() {
		return post;
	}

}
